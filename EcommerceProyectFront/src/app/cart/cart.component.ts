import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../services/product/product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  displayedColumns: string[] = ['Nombre', 'Descripción', 'Precio', 'Precio con descuento', 'Acción'];
  cartDetails : any[] = [];

  constructor(private productService : ProductService,
    private router : Router) { }

  ngOnInit(): void {
    this.getCartDetails();
  }

  delete(cartId){
    console.log(cartId)
    this.productService.deleteCartItem(cartId).subscribe(
      (resp) => {
        console.log(resp);
        this.getCartDetails();

      },(error) =>{
        console.log(error);
      }
    )
  }

  getCartDetails(){

    this.productService.getCartDetails().subscribe(
      (response : any[]) => {
        console.log(response)
        this.cartDetails = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  checkout(){
    this.router.navigate(['/buyProduct', {
      isSingleProductCheckout: false, id: 0
    }]);

  }

}
