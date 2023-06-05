import {Inject, Injectable, OnDestroy} from '@angular/core';
import {WebSocketSubject, WebSocketSubjectConfig} from "rxjs/internal/observable/dom/WebSocketSubject";
import {IWsMessage, WebSocketConfig} from "./websocket.interfaces";
import {
  distinctUntilChanged,
  filter,
  interval, map,
  Observable,

  share,
  Subject,
  SubscriptionLike,
  takeWhile
} from "rxjs";
import { config } from './websocket.config';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements OnDestroy{
  private readonly config: WebSocketSubjectConfig<IWsMessage<any>>;

  private websocketSub: SubscriptionLike;
  private statusSub: SubscriptionLike;

  private reconnection$: Observable<number>|null=null;
  private websocket$: WebSocketSubject<IWsMessage<any>>|null=null;

  private wsMessages$: Subject<IWsMessage<any>>;

  private reconnectInterval: number;
  private reconnectAttempts: number;
  private isConnected: boolean =false;
  public status: Observable<boolean>;

  constructor(@Inject(config) private wsConfig: WebSocketConfig) {

    this.status = new Observable<boolean>(() => {
    }).pipe(share(), distinctUntilChanged());
    this.wsMessages$ = new Subject<IWsMessage<any>>();
    this.reconnectInterval = wsConfig.reconnectInterval || 5000; // pause between connections
    this.reconnectAttempts = wsConfig.reconnectAttempts || 10; // number of connection attempts
    this.config = {
      url: wsConfig.url,
      closeObserver: {
        next: () => {
          this.websocket$ = null;
        }
      },
      openObserver: {
        next: () => {
          console.log('WebSocket connected!');
        }
      }
    };



    this.statusSub = this.status
      .subscribe((isConnected) => {
        this.isConnected = isConnected;

        if (!this.reconnection$ && !isConnected) {
          this.reconnect();
        }
      });
    this.websocketSub = this.wsMessages$.subscribe(
      null, (error: ErrorEvent) => console.error('WebSocket error!', error)
    );

    this.connect();
  }

  private reconnect(): void {
    this.reconnection$ = interval(this.reconnectInterval)
      .pipe(takeWhile((v, index) => index < this.reconnectAttempts && !this.websocket$));

    this.reconnection$.subscribe(
      () => this.connect(),
      null,
      () => {
        // Subject complete if reconnect attemts ending
        this.reconnection$ = null;
        if (!this.websocket$) {
          this.wsMessages$.complete();
        }
      });
  }
  private connect(): void {
    this.websocket$ = new WebSocketSubject(this.config);
    this.websocket$.subscribe(
      (message) => this.wsMessages$.next(message),
      (error: Event) => {
        if (!this.websocket$) {

          this.reconnect();
        }
      });
  }

  ngOnDestroy() {
    this.websocketSub.unsubscribe();
    this.statusSub.unsubscribe();
  }
  public on<T>(event: string): Observable<T> {
      return this.wsMessages$.pipe(
        filter((message: IWsMessage<T>) => message.event === event),
        map((message: IWsMessage<T>) => message.data)
      );
  }


  /*
  * on message to server
  * */
  public send(event: string, data: any = {}): void {
    if (this.websocket$) {
      this.websocket$.next(<any>JSON.stringify({ event, data }));
    } else {
      console.error('Send error!');
    }
  }
}
