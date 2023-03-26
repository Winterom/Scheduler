import {Injectable} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {EventBusService} from "../services/eventBus/event-bus.service";
import {AuthenticationAPI} from "../services/API/AuthenticationAPI";
import {UserService} from "../services/auth/user.service";

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private api: AuthenticationAPI = new AuthenticationAPI();

  constructor(private userService: UserService, private eventBus: EventBusService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let authReq = request;
    let token=null;
    if(!this.userService.isTokenExpire()){
      token = this.userService.token;
    }
    if (token != null) {
      authReq = request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
    return next.handle(authReq);
  }
}
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
