//To-do: dodati production environment nakon prvog deploy-a
const ENVIRONMENTS = {
  'localhost': 'dev'
};

const BACKEND_HOSTNAMES = {
  dev: 'http://localhost:8888',
  production: ''
};

const APP_HOSTNAMES = {
  dev: 'http://localhost:4200',
  production: ''
};

const API_ROUTE = '/api';

export {
  ENVIRONMENTS,
  BACKEND_HOSTNAMES,
  APP_HOSTNAMES,
  API_ROUTE
}
