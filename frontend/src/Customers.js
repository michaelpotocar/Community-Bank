import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import Context from './Context';
import { Link } from 'react-router-dom';
import {
  Button,
  Paper,
  Grid,
  Typography,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from '@mui/material';

function Customers() {
  const { api_id } = useContext(Context);
  const [loading, setLoading] = useState(true);
  const [customers, setCustomers] = useState("Loading");

  useMemo(() => {
    if (api_id !== '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers`)
        .then(response => {
          setCustomers(response.data.customers);
          setLoading(false);
        }).catch(err => {
          console.log(err);
        });
    }
  }, [api_id]);

  return (
    !loading &&
    <Grid container
      spacing={1}
      justifyContent="center">

      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid container
        justifyContent="center"
        spacing={1}>

        <Grid item xs={12} />

        <Grid item xs={12}>
          <Typography align='center' variant="h3">
            Welcome, Please make a Selection
          </Typography>
        </Grid>

        <Grid item xs={12} />
        <Grid item xs={12} />

        <Grid item xs={9}>
          <TableContainer component={Paper}>
            <Table >
              <TableHead>
                <TableRow>
                  <TableCell align="center">Customer</TableCell>
                  <TableCell align="center">Details</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {customers.map(customer => (
                  <TableRow
                    key={customer.customerId}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell align="center">{customer.firstName} {customer.lastName}</TableCell>
                    <TableCell align="center">
                      <Link to={`/customer/${customer.id}`} >
                        <Button variant="contained" fullWidth={true}>View Accounts</Button>
                      </Link>

                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Grid>

    </Grid>
  );
};

export default Customers;

