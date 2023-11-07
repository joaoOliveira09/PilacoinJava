import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './pages/about/about.component';
import { CarteiraComponent } from './pages/carteira/carteira.component';
import { EventosComponent } from './pages/eventos/eventos.component';
import { TranferenciaComponent } from './pages/tranferencia/tranferencia.component';

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch:'full'},
  {path: 'about', component: AboutComponent},
  {path: 'carteira', component: CarteiraComponent},
  {path: 'eventos', component: EventosComponent},
  {path: 'transferencia', component: TranferenciaComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
