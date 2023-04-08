import { useState, useMemo, useContext, useEffect } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Button, Grid, Typography, FormControl, InputLabel, Select, MenuItem, TextField } from '@mui/material';

export default function CreateAccount() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();

  const [loading, setLoading] = useState([true]);
  const [customer, setCustomer] = useState({});

  const [accountTypeField, setAccountTypeField] = useState('');

  const [nicknameField, setNicknameField] = useState('');
  const [nicknameFieldError, setNicknameFieldError] = useState(false);
  const [nicknameFieldErrorMessage, setNicknameFieldErrorMessage] = useState('');

  const [accountNumberField, setAccountNumberField] = useState('');
  const [accountNumberFieldError, setAccountNumberFieldError] = useState(false);
  const [accountNumberFieldErrorMessage, setAccountNumberFieldErrorMessage] = useState('');

  const [routingNumberField, setRoutingNumberField] = useState('');
  const [routingNumberFieldError, setRoutingNumberFieldError] = useState(false);
  const [routingNumberFieldErrorMessage, setRoutingNumberFieldErrorMessage] = useState('');

  const [creditLimitField, setCreditLimitField] = useState(() => { return Math.ceil(Math.random() * 7) * 500; });
  const [creditLimitFieldError, setCreditLimitFieldError] = useState(false);
  const [creditLimitFieldErrorMessage, setCreditLimitFieldErrorMessage] = useState('');

  const [submitDisabled, setSubmitDisabled] = useState(true);
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

  function validateNicknameField() {
    const regex = /^[a-zA-Z0-9 ]{3,16}$/;
    const regexPass = regex.test(nicknameField.trim());
    if (!regexPass) {
      setNicknameFieldError(true);
      setNicknameFieldErrorMessage("Nickname must be 3-16 characters.");
      return true;
    }

    const originalityCheck = !customer.accounts.map(account => account.nickname.trim().toLowerCase())
      .includes(nicknameField.trim().toLowerCase());
    if (!originalityCheck) {
      setNicknameFieldError(true);
      setNicknameFieldErrorMessage("Nickname must original.");
      return true;
    }

    setNicknameFieldError(false);
    setNicknameFieldErrorMessage('');
    return false;
  };

  function validateCreditLimitField() {
    const regex = /^\d{3,6}$/;
    const regexPass = regex.test(creditLimitField);
    if (!regexPass) {
      setCreditLimitFieldError(true);
      setCreditLimitFieldErrorMessage("Credit Limit must be 3-6 decimals.");
      return true;
    }

    setCreditLimitFieldError(false);
    setCreditLimitFieldErrorMessage('');
    return false;
  };

  function validateAccountNumberField() {
    const regex = /^\d{6,16}$/;
    const regexPass = regex.test(accountNumberField);
    if (!regexPass) {
      setAccountNumberFieldError(true);
      setAccountNumberFieldErrorMessage("Account Number must be 6-16 decimals.");
      return true;
    }

    setAccountNumberFieldError(false);
    setAccountNumberFieldErrorMessage('');
    return false;
  };

  function validateRoutingNumberField() {
    const regex = /^\d{9}$/;
    const regexPass = regex.test(routingNumberField);
    if (!regexPass) {
      setRoutingNumberFieldError(true);
      setRoutingNumberFieldErrorMessage("Routing Number must be 9 decimals.");
      return true;
    }

    setRoutingNumberFieldError(false);
    setRoutingNumberFieldErrorMessage('');
    return false;
  };

  function validateSubmitButton() {
    let invalidNickname = validateNicknameField();
    if (accountTypeField == 'savings' || accountTypeField == 'checking') {
      setSubmitDisabled(invalidNickname);
    } else if (accountTypeField == 'credit') {
      let invalidCreditLimit = validateCreditLimitField();
      setSubmitDisabled(invalidNickname || invalidCreditLimit);
    } else if (accountTypeField == 'external') {
      let invalidAccountNumber = validateAccountNumberField();
      let invalidRoutingNumber = validateRoutingNumberField();
      setSubmitDisabled(invalidNickname || invalidAccountNumber || invalidRoutingNumber);
    } else {
      setSubmitDisabled(true);
    }
  }

  useEffect(() => {
    validateSubmitButton();
  });

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
              onChange={(event) => { setAccountTypeField(event.target.value); }} >
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
              onChange={(event) => { setNicknameField(event.target.value); }} />
          </FormControl>

          {accountTypeField == 'external' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Account Number"
                variant="outlined"
                error={accountNumberFieldError}
                helperText={accountNumberFieldErrorMessage}
                value={accountNumberField}
                onChange={(event) => { setAccountNumberField(event.target.value.trim()); }} />
            </FormControl>}

          {accountTypeField == 'external' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Routing Number"
                variant="outlined"
                error={routingNumberFieldError}
                helperText={routingNumberFieldErrorMessage}
                value={routingNumberField}
                onChange={(event) => { setRoutingNumberField(event.target.value.trim()); }} />
            </FormControl>}

          {accountTypeField == 'credit' &&
            <FormControl fullWidth margin="normal">
              <TextField
                label="Credit Limit"
                variant="outlined"
                error={creditLimitFieldError}
                helperText={creditLimitFieldErrorMessage}
                disabled={true}
                value={creditLimitField} />
            </FormControl>}

          <FormControl fullWidth margin="normal">
            <Button
              variant="contained"
              onClick={submitter}
              disabled={submitDisabled}>
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
