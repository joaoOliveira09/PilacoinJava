import { Component, OnInit } from '@angular/core';
import { Pilacoin } from 'src/app/interfaces/Pilacoin';
import { PilacoinService } from 'src/app/service/pilacoin.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  allPilas: Pilacoin[] = []
  pilas: Pilacoin[] = []
  baseApiUrl = environment.API_URL

  constructor(private pilacoinService: PilacoinService) { }

  ngOnInit(): void {

    this.pilacoinService.getPilas().subscribe((items) =>{
      console.log(items)
      this.allPilas = items as Pilacoin[]
      this.pilas = items as Pilacoin[]     
    }) 
  }

  search(event: Event): void{

    const target = event.target as HTMLInputElement
    const value = target.value

    this.pilas = this.allPilas.filter(pila => {
     return pila.nonce!.toString().includes(value)
    })

  }

}
