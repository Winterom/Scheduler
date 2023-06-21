import {Injectable, OnDestroy} from '@angular/core';
import {WebSocketSubject} from "rxjs/webSocket";
import {IWsMessage, IWsMessageBody} from "./websocket.interfaces";
import {
  distinctUntilChanged,
  filter,
  interval,
  map,
  Observable,
  Observer,
  share,
  Subject,
  SubscriptionLike,
  takeWhile
} from "rxjs";
import {WebSocketSubjectConfig} from "rxjs/internal/observable/dom/WebSocketSubject";
import {UserService} from "../../user.service";



@Injectable({
  providedIn: 'root'
})
export class WebsocketService implements OnDestroy{
  private isConnected:boolean =false;
  public wsSubject:WebSocketSubject<any>|null=null;
  private wsMessages: Subject<IWsMessage<any>> = new Subject<IWsMessage<any>>();
  private readonly config: WebSocketSubjectConfig<IWsMessage<any>>;
  private connection$: Observer<boolean>;
  private websocketSub: SubscriptionLike;
  private reconnection$: Observable<number>|null =null;
  private reconnectInterval: number = 5000;
  private reconnectAttempts: number = 10;
  public status: Observable<boolean>;
  constructor(private userService:UserService) {
    this.connection$ = {next:(value)=>{
          this.isConnected = value;
      },error:(err)=>{
        console.error(err)
      },complete:()=>{
        console.log('подписка завершена')
      }}
    this.config = {
      url : '',
      closeObserver: {
        next: () => {
          this.wsSubject = null;
          this.connection$.next(false);
          console.log('WebSocket disconnected')
        }
      },
      openObserver: {
        next: () => {
          console.log('WebSocket connected!');
          this.connection$.next(true);
        }
      }
    };
    this.websocketSub = this.wsMessages.subscribe(
      {next:data => {},error:err => {
        console.log(err)}
      }
    );
    this.status = new Observable<boolean>((observer) => {
      this.connection$ = observer;
    }).pipe(share(), distinctUntilChanged());
  }
  public connect(url:string): void {
    this.config.url = url + '?bearer=' + this.userService.token;
    this.wsSubject = new WebSocketSubject(this.config); // создаем
// если есть сообщения, шлем их в дальше,
// если нет, ожидаем
// реконнектимся, если получили ошибку
    this.wsSubject.subscribe({next:message =>{
      this.wsMessages.next(message)
      },error:err => {
      console.log(err)
      this.reconnect(url)
      }});
  }
  reconnect(url:string) {
    this.reconnection$ = interval(this.reconnectInterval)
      .pipe(takeWhile((v, index) => index < this.reconnectAttempts && !this.wsSubject));
    this.reconnection$.subscribe(
      {next:value => {this.connect(url)},
    error:err => {},complete:() =>{
      this.reconnection$ = null;
      if (!this.wsSubject) {
        this.wsMessages.complete();
        this.connection$.complete();
      }}});
  }
  ngOnDestroy(): void {
    this.websocketSub.unsubscribe();
  }
  public on<T>(event: string): Observable<IWsMessageBody<T>> {
      return this.wsMessages.pipe(
        filter((message: IWsMessage<T>) => message.event === event),
        map((message: IWsMessage<T>) => message.payload)
      );
  }


  /*
  * on message to server
  * */
  public send(event: string, data: any = {}): void {
    if (this.wsSubject) {
      this.wsSubject.next(JSON.stringify({ event:event.toString(), data:data }));
    } else {
      console.error('Send error! connection is null');
    }
  }
}
