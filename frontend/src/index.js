import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import CustomerDetails from './App';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

const root = ReactDOM.createRoot(document.getElementById('root'));

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

root.render(
  <React.StrictMode>
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <CustomerDetails />
    </ThemeProvider>
  </React.StrictMode>
);