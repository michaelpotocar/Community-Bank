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
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField
} from '@mui/material';
import { styled } from '@mui/material/styles';

function CreateAccount() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();
  const [loading, setLoading] = useState([true]);
  const [customer, setCustomer] = useState('Loading');
  const [accounts, setAccounts] = useState('Loading');

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
  const [creditLimitField, setCreditLimitField] = useState('');

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
    console.log();
    axios.post(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/createaccount`, {
      type: accountTypeField,
      nickname: nicknameField,
      accountNumber: accountNumberField,
      routingNumber: routingNumberField,
      creditLimit: creditLimitField
    })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

  };

  return (
    !loading.includes(true) &&

    <Grid container
      spacing={1}
      justifyContent="center">

      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={4} />
      <Grid item xs={4}>
        <Typography align='center' variant="h4">
          Create a new Account
        </Typography>
      </Grid>
      <Grid item xs={4} />

      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={4}>
        <Grid container
          justifyContent="center">
          <FormControl fullWidth>

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

            <TextField
              label="Nickname"
              variant="outlined"
              error={nicknameFieldError}
              helperText={nicknameFieldErrorMessage}
              value={nicknameField}
              onChange={changeNicknameField}
              margin="normal" />

            {accountTypeField == 'external' && <TextField
              label="Account Number"
              variant="outlined"
              error={accountNumberFieldError}
              helperText={accountNumberFieldErrorMessage}
              value={accountNumberField}
              onChange={changeAccountNumberField}
              margin="normal" />}

            {accountTypeField == 'external' && <TextField
              label="Routing Number"
              variant="outlined"
              error={routingNumberFieldError}
              helperText={routingNumberFieldErrorMessage}
              value={routingNumberField}
              onChange={changeRoutingNumberField}
              margin="normal" />}

            {accountTypeField == 'credit' && <TextField
              label="Credit Limit"
              variant="outlined"
              disabled={true}
              value={creditLimitField}
              margin="normal" />}

            <Button
              variant="contained"
              onClick={submitter}>
              Submit
            </Button>

          </FormControl>
        </Grid >
      </Grid>

    </Grid >
  );
};

export default CreateAccount;
