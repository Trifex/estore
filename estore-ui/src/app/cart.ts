import { Product } from "./product";

export class Cart {
    user: string
    items: Array<Product>
    total: number

    constructor(user: string) {
        this.user = user;
        this.items = new Array<Product>();
        this.total = 0;
    }
}