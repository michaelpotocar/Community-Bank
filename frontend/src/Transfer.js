import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
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

function Transfer() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();
  const [loading, setLoading] = useState([true, true]);
  const [customer, setCustomer] = useState('Loading');
  const [accounts, setAccounts] = useState('Loading');

  useMemo(() => {
    if (api_id !== '') {
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

  return (
    !loading.includes(true) &&

    <Grid container
      spacing={1}
      justifyContent="center">

    </Grid>
  );
};

export default Transfer;
