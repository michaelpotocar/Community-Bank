import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import { Container } from '@mui/material';
import Context from './Context';
import { useParams, Link } from 'react-router-dom';
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
import { styled } from '@mui/material/styles';

function CustomerAccounts() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();
  const [loading, setLoading] = useState([true, true]);
  const [customer, setCustomer] = useState('Loading');
  const [accounts, setAccounts] = useState('Loading');

  useMemo(() => {
    if (api_id != '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}`)
        .then(response => {
          setCustomer(response.data.customer);
          setLoading(loading => { return [false, loading[1]]; });
        }).catch(err => {
          console.log(err);
        });

      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/accounts`)
        .then(response => {
          setAccounts(response.data.accounts);
          setLoading(loading => { return [loading[0], false]; });
        }).catch(err => {
          console.log(err);
        });
    }
  }, [api_id]);

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  }));

  return (
    !loading.includes(true) &&

    <>
      <Grid container spacing={1}
        justifyContent="center"
      >
        <Grid item xs={3} >
        </Grid>
        <Grid item xs={9} />

        <Grid item xs={9} >
        </Grid>

        <Grid container
          justifyContent="center"
          spacing={1}>

          <Grid item xs={12} />

          <Grid item xs={1} />
          <Grid item xs={3}>
            <Link to={`/`} >
              <Button variant="contained" > Return&nbsp;to Customer&nbsp;Selection</Button>
            </Link>
          </Grid>
          <Grid item xs={8} />

          <Grid item xs={12}>
            <Typography align='center' variant="h2">
              Hi {customer.firstName}!
            </Typography>
          </Grid>

          <Grid item xs={9}>
            <TableContainer component={Paper}>
              <Table >
                <TableHead>
                  <TableRow>
                    <TableCell align="center">Account</TableCell>
                    <TableCell align="center">Balance</TableCell>
                    <TableCell align="center">Details</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {accounts.filter(account => account.type !== 'external').map(account => (
                    <TableRow
                      key={account.accountId}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell align="center">{account.nickname}</TableCell>
                      <TableCell align="center">Balance: ${(Math.round(account.balance * 100) / 100).toFixed(2)}</TableCell>
                      <TableCell align="center">
                        <Link to={`/customer/${customerId}/account/${account.accountId}`} >
                          <Button variant="contained" fullWidth={true} >View Transactions</Button>
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
    </>
  );
};

export default CustomerAccounts;


