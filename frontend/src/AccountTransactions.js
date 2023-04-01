import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import { Button, Container, Paper, Grid, Typography } from '@mui/material';
import Context from './Context';
import { useParams, Link } from 'react-router-dom';
import moment from 'moment';

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
      <Container maxWidth='md' disableGutters={false}>
        <Typography align='center' variant="h2">
          {account.nickname} - Balance: ${(Math.round(account.balance * 100) / 100).toFixed(2)}
        </Typography>
      </Container>

      <Container maxWidth='md' disableGutters={false}>
        {transactions.map(transaction => {
          return (
            <Grid container spacing={5}>
              <Grid item xs={8}>
                <Item>{moment.unix(transaction.completedDateTime).format('dddd, MMMM Do, YYYY')} - {transaction.memo} Amount: ${(Math.round(transaction.amount * 100) / 100).toFixed(2)}</Item>
              </Grid>
            </Grid>
          );
        })}
      </Container>

    </>
  );
};

export default AccountTransactions;