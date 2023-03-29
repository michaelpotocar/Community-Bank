import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import { Button, Container, Paper, Grid, Typography } from '@mui/material';
import Context from './Context';
import { useParams, Link } from 'react-router-dom';

function TransactionList() {
  const { api_id } = useContext(Context);
  const { customerId, accountId } = useParams();
  const [loading, setLoading] = useState(true);
  const [resp, setResp] = useState('Loading');

  useMemo(() => {
    if (api_id != '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}`)
        .then(response => {
          setResp(response.data);
          setLoading(false);
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
    !loading &&

    <>
      <Container maxWidth='md' disableGutters={true}>
        <Typography align='center' variant="h2">
          Hi {resp.firstName}!
        </Typography>
      </Container>

      <Container maxWidth='md' disableGutters={true}>
        {resp.accounts.filter(a => a.type !== 'external').map(account => {
          return (
            <Grid container spacing={5}>
              <Grid item xs={8}>
                <Item>{account.nickname} Balance: ${(Math.round(account.balance * 100) / 100).toFixed(2)}</Item>
              </Grid>
              <Grid item xs={4}>
                <Container>
                  <Button variant="contained" fullWidth={true} >View Transactions</Button>
                </Container>
              </Grid>
            </Grid>
          );
        })}
      </Container>

    </>
  );
};

export default TransactionList;