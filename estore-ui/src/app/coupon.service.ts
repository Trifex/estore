import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { Coupon } from "./coupon"

@Injectable({
  providedIn: 'root'
})
export class CouponService {

  private couponURL = 'http://localhost:8080/coupons'
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getCoupons(): Observable<Coupon[]>{
    return this.http.get<Coupon[]>(this.couponURL).pipe(catchError(this.handleError<Coupon[]>('getCoupons', [])));
  }

  getCoupon(name: string): Observable<Coupon>{
    const url = `${this.couponURL}/${name}`;
    return this.http.get<Coupon>(url).pipe(catchError(this.handleError<Coupon>(`getCoupon id=${name}`)));
  }

  updateCoupon(coupon: Coupon): Observable<any> {
    return this.http.put(this.couponURL, coupon, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateCoupon'))
    );
  }

  createCoupon(coupon: Coupon): Observable<Coupon>{
    return this.http.post<Coupon>(this.couponURL, coupon, this.httpOptions).pipe(
      catchError(this.handleError<Coupon>('createCoupon'))
    );
  }

  deleteCoupon(name: String): Observable<Coupon>{
    const url = `${this.couponURL}/${name}`;
    return this.http.delete<Coupon>(url).pipe(catchError(this.handleError<Coupon>( `deleteCoupon id=${name}`)))
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}