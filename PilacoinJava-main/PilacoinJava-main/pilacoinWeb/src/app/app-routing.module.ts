import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './pages/about/about.component';
import { EventosComponent } from './pages/eventos/eventos.component';
import { TranferenciaComponent } from './pages/tranferencia/tranferencia.component';
import {HomeComponent} from "./pages/home/home.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch:'full'},
  {path: 'home', component: HomeComponent},
  {path: 'about', component: AboutComponent},
  {path: 'eventos', component: EventosComponent},
  {path: 'home/transferencia/:id', component: TranferenciaComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
