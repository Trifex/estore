import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Product } from './product';

import { catchError, tap } from 'rxjs/operators';

import { Observable, of } from 'rxjs';
import { Cart } from './cart';
import { Coupon } from './coupon';
import { CouponService } from './coupon.service';

@Injectable({
    providedIn: 'root'
  })
export class CartService {
    
    private cartURL = 'http://localhost:8080/carts';
    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      };
    
      constructor(private http: HttpClient) { }

      getCart(name: string): Observable<Cart> {
        const url = `${this.cartURL}/${name}`
        return this.http.get<Cart>(url)
          .pipe(
            catchError(this.handleError<Cart>('getCart'))
          )
      }

      createCart(name: string): Observable<Cart> {
        const url = `${this.cartURL}/create`
        return this.http.post<Cart>(url, name, this.httpOptions)
          .pipe(
            catchError(this.handleError<Cart>('createCart'))
          )
      }

      addToCart(name: string, product: Product): Observable<Cart> {
        const url = `${this.cartURL}/${product.id}/${name}/add`
        return this.http.put<Cart>(url, this.httpOptions)
          .pipe(
            catchError(this.handleError<Cart>('addToCart'))
          )
      }

      removeFromCart(name: string, product: Product): Observable<Cart> {
        const url = `${this.cartURL}/${product.id}/${name}/remove`
        return this.http.put<Cart>(url, this.httpOptions)
          .pipe(
            catchError(this.handleError<Cart>('removeFromCart'))
          )
      }

      checkout(name: string): Observable<Cart> {
        const url = `${this.cartURL}/${name}`
        return this.http.delete<Cart>(url, this.httpOptions)
          .pipe(
            catchError(this.handleError<Cart>('checkout'))
          )
      }
      
      applyCoupon(name: string, couponName: String) {
        const url = `${this.cartURL}/${name}/apply`
        return this.http.put<Cart>(url, couponName, this.httpOptions)
          .pipe( 
            catchError(this.handleError<Cart>('applyCoupon'))
          )
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