
export namespace ChronosUtils{

  export enum Chronos{
    MILLIS='MILLIS',
    SECONDS='SECONDS',
    MINUTES='MINUTES',
    HOURS='HOURS',
    DAYS='DAYS',
    WEEKS='WEEKS',
    MONTHS='MONTHS',
    YEARS='YEARS',
  }


  /*Склонение зависит от последней цифры числа.
  Секунда: 1.
  Секунд: 0, 5, 6, 7, 8, 9.
  Секунды: 2, 3, 4.*/
  const chronosMap:Map<Chronos, string[]>= new Map()
      .set(Chronos.MILLIS,['миллисекунда','миллисекунд','миллисекунды'])
      .set(Chronos.SECONDS,['секунда','секунд','секунды'])
      .set(Chronos.MINUTES,['минута','минут','минуты'])
      .set(Chronos.HOURS,['час','часов','часа'])
      .set(Chronos.DAYS,['день','дней','дня'])
      .set(Chronos.WEEKS,['неделю','недель','недели'])
      .set(Chronos.MONTHS,['месяц','месяцев','месяца'])
      .set(Chronos.YEARS,['год','лет','года']);


  export function chronosToString(chronos:Chronos,count:number):string|null{
    const num10 = count % 10;
    const entity = chronosMap.get(chronos);
    if(entity===undefined) {
      return null;
    }
    if(num10===1){
        return entity[0];
    }
    if(num10>1&&num10<5){
      return entity[2];
    }
    return entity[1];
  }

}
