import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import { Button, Container, Paper, Grid, Typography } from '@mui/material';
import Context from './Context';
import { useParams, Link } from 'react-router-dom';

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
      <Container maxWidth='md' disableGutters={false}>
        <Typography align='center' variant="h2">
          Hi {customer.firstName}!
        </Typography>
      </Container>

      <Container maxWidth='md' disableGutters={false}>
        {accounts.filter(account => account.type !== 'external').map(account => {
          return (
            <Grid container spacing={5}>
              <Grid item xs={8}>
                <Item>{account.nickname} Balance: ${(Math.round(account.balance * 100) / 100).toFixed(2)}</Item>
              </Grid>
              <Grid item xs={4}>
                <Container>
                  <Link to={`/customer/${customerId}/account/${account.accountId}`} >
                    <Button variant="contained" fullWidth={true} >View Transactions</Button>
                  </Link>
                </Container>
              </Grid>
            </Grid>
          );
        })}
      </Container>

    </>
  );
};

export default CustomerAccounts;