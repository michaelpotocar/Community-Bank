import './App.css';
import { useState, useMemo } from "react";
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Context from './Context';
import CustomerList from './CustomerList';
import CustomerInfo from './CustomerInfo';

function App() {

    const [api_id, set_api_id] = useState("");
    const [page, setPage] = useState("CustomerList");
    const [customerId, setCustomerId] = useState();
    useMemo(() => {
        fetch('/.ignore/api_id').then(res => {
            return res.text();
        }).then(res => {
            set_api_id(res);
        });
    }, []);

    const darkTheme = createTheme({
        palette: {
            mode: 'dark',
        },
    });

    const contextBody = {
        api_id,
        page,
        setPage,
        customerId,
        setCustomerId,
    };

    return (
        <>
            <ThemeProvider theme={darkTheme}>
                <CssBaseline />
                <Context.Provider value={contextBody}>
                    {
                        (page == "CustomerList" && <CustomerList />) ||
                        (page == "CustomerInfo" && <CustomerInfo />)
                    }
                </Context.Provider>
            </ThemeProvider>
        </>
    );
}

export default App;
