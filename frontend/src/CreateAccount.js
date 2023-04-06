import { useState, useMemo, useContext } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link, Navigate, useNavigate } from 'react-router-dom';
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
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField
} from '@mui/material';
import { styled } from '@mui/material/styles';

export default function CreateAccount() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();
  const [loading, setLoading] = useState([true]);
  const [customer, setCustomer] = useState('Loading');

  const [accountTypeField, setAccountTypeField] = useState('');
  const [nicknameField, setNicknameField] = useState('');
  const [nicknameFieldError, setNicknameFieldError] = useState(false);
  const [nicknameFieldErrorMessage, setNicknameFieldErrorMessage] = useState('');
  const [accountNumberField, setAcocuntNumberField] = useState('');
  const [accountNumberFieldError, setAcocuntNumberFieldError] = useState(false);
  const [accountNumberFieldErrorMessage, setAccountNumberFieldErrorMessage] = useState('');
  const [routingNumberField, setRoutingNumberField] = useState('');
  const [routingNumberFieldError, setRoutingNumberFieldError] = useState(false);
  const [routingNumberFieldErrorMessage, setRoutingNumberFieldErrorMessage] = useState('');
  const [creditLimitField, setCreditLimitField] = useState(() => {
    return Math.ceil(Math.random() * 7) * 500;
  });

  const navigate = useNavigate();

  useMemo(() => {
    if (api_id !== '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}`)
        .then(response => {
          setCustomer(response.data.customer);
          setLoading([false]);
        }).catch(err => {
          console.log(err);
        });
    }
  }, [api_id]);

  const changeNicknameField = (event) => {
    const input = event.target.value;
    setNicknameField(input);

    const error = customer.accounts.map(account => account.nickname.trim().toLowerCase())
      .includes(input.trim().toLowerCase());
    if (error) {
      setNicknameFieldError(true);
      setNicknameFieldErrorMessage("You already have an account with that nickname");
    } else {
      setNicknameFieldError(false);
      setNicknameFieldErrorMessage('');
    }
  };

  const changeAccountNumberField = (event) => {
    const input = event.target.value;
    setAcocuntNumberField(input);

    const positiveIntegerRegex = /^\d{0,16}$/;
    const error = !positiveIntegerRegex.test(input);
    if (error) {
      setAcocuntNumberFieldError(true);
      setAccountNumberFieldErrorMessage("Can only contain up to sixteen digits");
    } else {
      setAcocuntNumberFieldError(false);
      setAccountNumberFieldErrorMessage('');
    }
  };

  const changeRoutingNumberField = (event) => {
    const input = event.target.value;
    setRoutingNumberField(input);

    const positiveIntegerRegex = /^\d{0,16}$/;
    const error = !positiveIntegerRegex.test(input);
    if (error) {
      setRoutingNumberFieldError(true);
      setRoutingNumberFieldErrorMessage("Can only contain up to sixteen digits");
    } else {
      setRoutingNumberFieldError(false);
      setRoutingNumberFieldErrorMessage('');
    }
  };

  const submitter = () => {
    axios.post(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/accounts`, {
      type: accountTypeField,
      nickname: nicknameField,
      accountNumber: accountNumberField,
      routingNumber: routingNumberField,
      creditLimit: creditLimitField
    })
      .then((response) => {
        navigate(`/customer/${customerId}`);
      })
      .catch((error) => {
        navigate('/error', { state: error });
      });
  };

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

      <Grid item xs={4} />
      <Grid item xs={4}>
        <Typography align='center' variant="h4">
          Create a new Account
        </Typography>
      </Grid>
      <Grid item xs={4} />

      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={8}>
        <Grid container
          justifyContent="center">

          <FormControl fullWidth margin="normal">
            <InputLabel>Account Type</InputLabel>
            <Select
              value={accountTypeField}
              label="Account Type"
              onChange={event => {
                setAccountTypeField(event.target.value);
              }}>
              <MenuItem value={"savings"}>Savings</MenuItem>
              <MenuItem value={"checking"}>Checking</MenuItem>
              <MenuItem value={"credit"}>Credit</MenuItem>
              <MenuItem value={"external"}>External</MenuItem>
            </Select>
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              label="Nickname"
              variant="outlined"
              error={nicknameFieldError}
              helperText={nicknameFieldErrorMessage}
              value={nicknameField}
              onChange={changeNicknameField} />
          </FormControl>

          {accountTypeField == 'external' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Account Number"
                variant="outlined"
                error={accountNumberFieldError}
                helperText={accountNumberFieldErrorMessage}
                value={accountNumberField}
                onChange={changeAccountNumberField} />
            </FormControl>}

          {accountTypeField == 'external' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Routing Number"
                variant="outlined"
                error={routingNumberFieldError}
                helperText={routingNumberFieldErrorMessage}
                value={routingNumberField}
                onChange={changeRoutingNumberField} />
            </FormControl>}

          {accountTypeField == 'credit' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Credit Limit"
                variant="outlined"
                disabled={true}
                value={creditLimitField} />
            </FormControl>}

          <FormControl fullWidth margin="normal">
            <Button
              variant="contained"
              onClick={submitter}>
              Submit
            </Button>
          </FormControl>

          <Grid item xs={12} />
          <Grid item xs={12} />
          <Grid item xs={12} />
          <Grid item xs={12} />
          <Grid item xs={12} />

        </Grid >
      </Grid>

    </Grid >
  );
};
