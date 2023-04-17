export interface EventData{
  name:AppEvents;
  value:any
}

export enum AppEvents{
  CHANGE_VISIBLE_SIDEBAR,
  INPUT_SHOW_HIDE_PASSWORD,
  LOGOUT,
  TABLE_COLUMN_CHANGE_SORT
}
