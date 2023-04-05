import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link } from 'react-router-dom';
import moment from 'moment';
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
import { currencyFormatted, wordFormatted } from './Utilities';

export default function AccountTransactions() {
  const { api_id } = useContext(Context);
  const { customerId, accountId } = useParams();
  const [loading, setLoading] = useState([true, true]);
  const [account, setAccount] = useState('Loading');
  const [transactions, setTransactions] = useState('Loading');

  useMemo(() => {
    if (api_id !== '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/accounts/${accountId}`)
        .then(response => {
          setAccount(response.data.account);
          setLoading(loading => { return [false, loading[1]]; });
        }).catch(err => {
          console.log(err);
        });

      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/accounts/${accountId}/transactions`)
        .then(response => {
          setTransactions(response.data.transactions.sort((a, b) => {
            if (a.completedDateTime > b.completedDateTime)
              return -1;
            if (a.completedDateTime < b.completedDateTime)
              return 1;
            return 0;
          }
          ));
          setLoading(loading => { return [loading[0], false]; });
        }).catch(err => {
          console.log(err);
        });

    }
  }, [api_id]);

  return (
    !loading.includes(true) &&
    <Grid container
      spacing={1}
      justifyContent="center">

      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={1} />
      <Grid item xs={3}>
        <Link to={`/customer/${customerId}`} >
          <Button variant="contained" >Return&nbsp;to Account&nbsp;Selection</Button>
        </Link>
      </Grid>
      <Grid item xs={8} />

      {account.type == 'credit' &&
        (<>
          <Grid item xs={2.5}>
            <Typography align='center' variant="h6">
              {account.nickname} {wordFormatted(account.type)}
            </Typography>
          </Grid>
          <Grid item xs={2.5}>
            <Typography align='center' variant="h6">
              Credit&nbsp;Limit: ${account.creditLimit}
            </Typography>
          </Grid>
          <Grid item xs={2.5}>
            <Typography align='center' variant="h6">
              Credit&nbsp;Available: {currencyFormatted(account.creditLimit - account.balance)}
            </Typography>
          </Grid>
          <Grid item xs={2.5}>
            <Typography align='center' variant="h6">
              Balance: {currencyFormatted(account.balance)}
            </Typography>
          </Grid>
        </>)
        ||
        (<>
          <Grid item xs={2} />
          <Grid item xs={3}>
            <Typography align='center' variant="h4">
              {account.nickname} {wordFormatted(account.type)}
            </Typography>
          </Grid>
          <Grid item xs={2} />
          <Grid item xs={3}>
            <Typography align='center' variant="h4">
              Balance: {currencyFormatted(account.balance)}
            </Typography>
          </Grid>
          <Grid item xs={2} />
        </>
        )}

      <Grid item xs={9}>
        <TableContainer component={Paper}>
          <Table >
            <TableHead>
              <TableRow>
                <TableCell align="center">Date</TableCell>
                <TableCell align="center">Memo</TableCell>
                <TableCell align="center">Amount</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {transactions.map((transaction) => (
                <TableRow
                  key={transaction.completedTime}
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                  <TableCell align="center">{moment.unix(transaction.completedDateTime).format('dddd, MMMM Do, YYYY')}</TableCell>
                  <TableCell align="center">{transaction.memo}</TableCell>
                  <TableCell align="center">{currencyFormatted(transaction.amount)}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Grid>

      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />

    </Grid>
  );
};
