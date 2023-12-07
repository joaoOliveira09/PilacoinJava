import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Transferencia } from 'src/app/interfaces/Transferencia';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TransferenciaService } from 'src/app/service/transferencia.service';
import { Usuario } from 'src/app/interfaces/Usuario';
import { UsuarioService } from 'src/app/service/usuario.service';
import { Pilacoin } from 'src/app/interfaces/Pilacoin';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-tranferencia',
  templateUrl: './tranferencia.component.html',
  styleUrls: ['./tranferencia.component.css']
})
export class TranferenciaComponent implements OnInit {

  @Output() onSubmit = new EventEmitter<Transferencia>()
  @Input() btnText!: String
  @Input() pilaData: Transferencia | null = null

  tranferenciaForm!: FormGroup;
  resultado!: number;
  usuarios: Usuario[] = []
  usuarioEspecifico: Usuario | undefined
  usuario: Usuario | undefined;

  constructor(private tranferenciaService: TransferenciaService,
     private usuarioService: UsuarioService,
     private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.usuarioService.getUsers().subscribe((items:Usuario[]) =>{
      this.usuarios = items
      console.log(this.usuarios)
    this.usuario = this.usuarios.find(usuario => usuario.id === this.usuario?.id);

      if (this.usuario) {
        this.usuarioEspecifico = this.usuario
      }

    })

    this.tranferenciaForm = new FormGroup({
       user: new FormControl('')
    })

  }

  selectedOption: string = ''

  onSelectChange(event: any){
    this.selectedOption = event.target.value;
    //this.selectedOption  = usuario.userId!
    console.log('Opção selecionada:', this.selectedOption)
    //this.usuarios[0] = this.selectedOption
  }

  Submit(){
     if(this.tranferenciaForm.invalid){
       return
     }
     const user: Usuario = {
       id : Number(this.selectedOption)
     }

    // this.tranferenciaForm.value. = user
    // console.log('machosta'+this.tranferenciaForm.value)

    const Pila: Pilacoin = {
      id :Number(this.route.snapshot.paramMap.get("id")!)
    }



    this.tranferenciaService.transferir(Number(this.selectedOption)!,Number(this.route.snapshot.paramMap.get("id")!)).subscribe()

  }

}
