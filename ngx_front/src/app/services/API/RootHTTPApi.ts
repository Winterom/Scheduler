export class RootHTTPApi {
  private host:string='http://localhost';
  private app_prefix:string='application';
  private api_version:string='api/v1';
  private port:string='8080';

  getRoot():string{
    return this.host + ':' + this.port + '/' + this.app_prefix + '/' + this.api_version;
  }
}
