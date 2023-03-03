import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { ProductCreatorComponent } from './product-creator/product-creator.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductEditorComponent } from './product-editor/product-editor.component';
import { ProductsComponent } from './products/products.component';
import { CartComponent } from './cart/cart.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: LoginpageComponent },
  { path: 'products/:name', component: ProductsComponent },
  { path: 'products/:name/edit', component: ProductCreatorComponent },
  { path: 'products/:name/edit/:id', component: ProductEditorComponent },
  { path: 'products/:id/:name', component: ProductDetailComponent },
  { path: 'home/:name', component: HomepageComponent },
  { path: 'carts/:name', component: CartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }