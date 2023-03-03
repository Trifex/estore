import { Component, Input, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: [ './product-search.component.css' ]
})
export class ProductSearchComponent implements OnInit {
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();
  @Input() deletable?: boolean;

  constructor(private productService: ProductService, private route: ActivatedRoute){ }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  getProductUrl(id: Number): String {
    const name = String(this.route.snapshot.paramMap.get('name'))
    return this.deletable ? `/products/admin/edit/${id}` : `/products/${id}/${name}`;
  }

  ngOnInit(): void {
    this.products$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.searchProducts(term)),
    );
  }
}