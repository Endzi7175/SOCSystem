import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SearchTableComponent } from './search-table/search-table.component';
import { AlarmTableComponent } from './alarm-table/alarm-table.component';


const routes: Routes = [
{ path: 'login', component:LoginComponent },
{ path: 'search', component:SearchTableComponent },
{ path: 'alarm', component:AlarmTableComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
