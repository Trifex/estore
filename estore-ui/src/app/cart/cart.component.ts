import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cart } from '../cart';
import { CartService } from '../cart.service';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
    selector: 'app-cart',
    templateUrl: './cart.component.html',
    styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
    cart!: Cart;
    username!: string;
    couponName: string = "";

    constructor(private cartService: CartService, 
        private productService: ProductService, 
        private route: ActivatedRoute,
        private router: Router) 
        {this.router.routeReuseStrategy.shouldReuseRoute = () => false; }

    ngOnInit(): void {
        this.getCurrentUser()
        this.makeCart()
        this.getCart()
    }

    getCurrentUser() {
        this.username = String(this.route.snapshot.paramMap.get('name'))
    }

    makeCart() {
        this.cart = new Cart(this.username);
    }

    getCart() {
        this.cartService.getCart(this.username)
          .subscribe(cart => this.cart = cart)
    }
    
    removeFromCart(product: Product) {
        this.cartService.removeFromCart(this.username, product).subscribe()
        product.copies++
        this.productService.updateProduct(product).subscribe()
        this.getCart()
    }

    checkout(): void {
        this.cartService.checkout(this.username).subscribe()
        this.getCart()
        location.reload()
    }

    applyCoupon(): void{
        this.cartService.applyCoupon(this.username, this.couponName).subscribe()
        this.getCart()
        location.reload()
    }
}