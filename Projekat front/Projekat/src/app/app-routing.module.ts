import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SearchTableComponent } from './search-table/search-table.component';
import { AlarmTableComponent } from './alarm-table/alarm-table.component';
import { RuleAdderComponent } from './rule-adder/rule-adder.component';
import { RoleAdderComponent } from './role-adder/role-adder.component';
import { RolePrivilageAdderComponent } from './role-privilage-adder/role-privilage-adder.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'search', component: SearchTableComponent },
  { path: 'alarm', component: AlarmTableComponent },
  { path: 'rule', component: RuleAdderComponent },
  { path: 'role', component: RoleAdderComponent },
  { path: 'privilage', component: RolePrivilageAdderComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
