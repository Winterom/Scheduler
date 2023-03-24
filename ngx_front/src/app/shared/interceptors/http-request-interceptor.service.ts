import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {EventBusService} from "../../services/eventBus/event-bus.service";
import {AppEvents, EventData} from "../../services/eventBus/EventData";
import {AuthenticationAPI} from "../../services/API/AuthenticationAPI";
import {UserService} from "../../services/auth/user.service";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private api:AuthenticationAPI= new AuthenticationAPI();
  constructor(private userService:UserService, private eventBus:EventBusService ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({
      withCredentials: true,
    });
    return next.handle(request).pipe(
      catchError((error) => {
        if (
          error instanceof HttpErrorResponse &&
          !request.url.includes(this.api.auth_api) &&
          error.status === 401
        ) {
          return this.handle401Error(request, next);
        }

        return throwError(() => error);
      })
    );
  }
  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;

      if (this.userService.isLogin()) {
        const logoutEvent:EventData ={name:AppEvents.LOGOUT,value:null}
        this.eventBus.emit(logoutEvent);
        }
      }
    return next.handle(request);
    }
}
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
