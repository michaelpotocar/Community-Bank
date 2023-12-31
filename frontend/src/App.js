import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Customers from './Customers';
import CustomerAccounts from './CustomerAccounts';
import AccountTransactions from './AccountTransactions';
import { Routes, Route } from 'react-router-dom';
import Context from './Context';
import { useState, useMemo } from 'react';
import CreateAccount from './CreateAccount';
import ReceivePayment from './ReceivePayment';
import Transfer from './Transfer';
import NotFound from './NotFound';
import Error from './Error';

export default function App() {

    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
        },
    });

    const [api_id, set_api_id] = useState('');
    useMemo(() => {
        fetch('/.ignore/api_id').then(res => {
            return res.text();
        }).then(res => {
            set_api_id(res);
        });
    }, []);

    const contextBody = {
        api_id,
    };

    return (
        <ThemeProvider theme={darkTheme}>
            <CssBaseline />
            <Context.Provider value={contextBody}>
                <Routes>
                    <Route path='/' element={<Customers />}> </Route>
                    <Route path='/customer/:customerId' element={<CustomerAccounts />}> </Route>
                    <Route path='/customer/:customerId/account/:accountId' element={<AccountTransactions />}> </Route>
                    <Route path='/customer/:customerId/createaccount' element={<CreateAccount />}> </Route>
                    <Route path='/customer/:customerId/transfer' element={<Transfer />}> </Route>
                    <Route path='/customer/:customerId/receivepayment' element={<ReceivePayment />}> </Route>
                    <Route path='/error' element={<Error />}> </Route>
                    <Route path='*' element={<NotFound />}> </Route>
                </Routes>
            </Context.Provider>
        </ThemeProvider>
    );
}
