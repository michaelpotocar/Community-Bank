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
import { styled } from '@mui/material/styles';

function AccountTransactions() {
  const { api_id } = useContext(Context);
  const { customerId, accountId } = useParams();
  const [loading, setLoading] = useState([true, true]);
  const [account, setAccount] = useState('Loading');
  const [transactions, setTransactions] = useState('Loading');

  useMemo(() => {
    if (api_id != '') {
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
      <Grid container
        justifyContent="center"
        spacing={1}>

        <Grid item xs={12} />

        <Grid item xs={1} />
        <Grid item xs={3}>
          <Link to={`/customer/${customerId}`} >
            <Button variant="contained" >Return&nbsp;to Account&nbsp;Selection</Button>
          </Link>
        </Grid>
        <Grid item xs={8} />

        <Grid item xs={12}>
          <Typography align='center' variant="h4">
            {account.nickname} - Balance: ${(Math.round(account.balance * 100) / 100).toFixed(2)}
          </Typography>
        </Grid>

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
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell align="center">{moment.unix(transaction.completedDateTime).format('dddd, MMMM Do, YYYY')}</TableCell>
                    <TableCell align="center">{transaction.memo}</TableCell>
                    <TableCell align="center">{(Math.round(transaction.amount * 100) / 100).toFixed(2)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>

      </Grid>




    </>
  );
};

export default AccountTransactions;