import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

const queryParams = new URLSearchParams(window.location.search)
const accessToken = queryParams.get("accessToken")

cookies.set('access-token', accessToken, { path: '/' });

const root = ReactDOM.createRoot(document.getElementById('app'));

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
