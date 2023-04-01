import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import { styled } from '@mui/material/styles';
import { Button, Container, Paper, Grid, Box, Typography } from '@mui/material';
import Context from './Context';
import { Link } from 'react-router-dom';

function Customers() {
  const { api_id } = useContext(Context);
  const [loading, setLoading] = useState(true);
  const [customers, setCustomers] = useState("Loading");

  useMemo(() => {
    if (api_id != '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers`)
        .then(response => {
          setCustomers(response.data.customers);
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
      <Container maxWidth='md' disableGutters={false}>
        <Typography align='center' variant="h2">
          Welcome!
        </Typography>
      </Container>

      <Container maxWidth='md' disableGutters={false}>
        {customers.map(customer => {
          return (
            <Grid container spacing={5}>
              <Grid item xs={8}>
                <Item>{customer.firstName} {customer.lastName}</Item>
              </Grid>
              <Grid item xs={4}>
                <Container>
                  <Link to={`/customer/${customer.id}`} >
                    <Button variant="contained" fullWidth={true}>View Accounts</Button>
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

export default Customers;

