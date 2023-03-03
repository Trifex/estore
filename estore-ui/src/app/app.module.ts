import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { AppRoutingModule } from './app-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { ProductEditorComponent } from './product-editor/product-editor.component';
import { ProductCreatorComponent } from './product-creator/product-creator.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { CartComponent } from './cart/cart.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    ProductDetailComponent,
    HomepageComponent,
    ProductEditorComponent,
    ProductCreatorComponent,
    ProductSearchComponent,
    LoginpageComponent,
    CartComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
