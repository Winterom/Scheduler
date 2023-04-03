
export class RootWSApi {
  private host:string='ws://localhost';
  private app_prefix:string='application';
  private api_version:string='api/v1';
  private port:string='8080';
  private postfix:string = 'ws';
  private applicationDestinationPrefixes:string='app';

  getWSRootApi():string{
    return this.host + ':' + this.port + '/' + this.app_prefix + '/' + this.api_version+'/'+this.postfix;
  }
  getDestinationPrefix():string{
    return this.applicationDestinationPrefixes;
  }
}
