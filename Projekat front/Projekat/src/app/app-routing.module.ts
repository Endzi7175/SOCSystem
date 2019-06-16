import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SearchTableComponent } from './search-table/search-table.component';

const routes: Routes = [
{ path: 'login', component:LoginComponent},
{ path: 'search', component:SearchTableComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
