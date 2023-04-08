import { useState, useMemo, useContext, useEffect } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Button, Grid, Typography, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import { currencyFormatted, wordFormatted } from './Utilities';

export default function ReceivePayment() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();

  const [loading, setLoading] = useState([true, true]);
  const [customer, setCustomer] = useState({});
  const [p2ps, setP2ps] = useState([]);

  const [paymentField, setPaymentField] = useState('');
  const [paymentFieldError, setPaymentFieldError] = useState(false);
  const [paymentFieldErrorMessage, setPaymentFieldErrorMessage] = useState('');

  const [targetAccountField, setTargetAccountField] = useState('');
  const [targetAccountFieldError, setTargetAccountFieldError] = useState(false);
  const [targetAccountFieldErrorMessage, setTargetAccountFieldErrorMessage] = useState('');

  const [submitDisabled, setSubmitDisabled] = useState(true);

  const navigate = useNavigate();

  useMemo(() => {
    if (api_id !== '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}`)
        .then(response => {
          setCustomer(response.data.customer);
          setLoading(loading => { return [false, loading[1]]; });
        }).catch(err => {
          console.log(err);
        });
    }

    if (api_id !== '') {
      axios.get(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/p2p`)
        .then(response => {
          setP2ps(response.data.p2ps);
          setLoading(loading => { return [loading[0], false]; });
        }).catch(err => {
          console.log(err);
        });
    }
  }, [api_id]);

  function validatePaymentField() {
    let validPaymentIds = p2ps?.map(p2p => p2p.transferId);

    if (!validPaymentIds?.includes(paymentField)) {
      setPaymentFieldError(true);
      setPaymentFieldErrorMessage("Must Select a valid Payment");
      return true;
    }

    setPaymentFieldError(false);
    setPaymentFieldErrorMessage('');
    return false;
  }

  function validateTargetAccountField() {
    const validAccountIds = customer.accounts?.filter(account => ['checking', 'savings', 'external'].includes(account.type))
      .map(account => account.accountId);

    if (!validAccountIds?.includes(targetAccountField)) {
      setTargetAccountFieldError(true);
      setTargetAccountFieldErrorMessage("Must Select a valid Account");
      return true;
    }

    setTargetAccountFieldError(false);
    setTargetAccountFieldErrorMessage('');
    return false;
  }

  function validateSubmitButton() {
    let invalidPaymentField = validatePaymentField();
    let invalidTargetAccountField = validateTargetAccountField();
    setSubmitDisabled(invalidPaymentField || invalidTargetAccountField);
  }

  useEffect(() => {
    validateSubmitButton();
  });

  const submitter = () => {
    axios.put(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/p2p/${paymentField}`, {
      targetAccountId: targetAccountField,
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
          Accept Payments
        </Typography>
      </Grid>
      <Grid item xs={4} />

      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={8}>
        <Grid container
          justifyContent="center">

          <FormControl fullWidth margin="normal">
            <InputLabel>Pending Payments</InputLabel>
            <Select
              value={paymentField}
              label="Pending Payments"
              error={paymentFieldError}
              helperText={paymentFieldErrorMessage}
              onChange={event => { setPaymentField(event.target.value); }}>
              {p2ps.map(p2p => {
                return (
                  <MenuItem
                    key={p2p}
                    value={p2p.transferId} >
                    {(() => {
                      const contact = customer.contacts.filter(contact => contact.id == p2p.fundingCustomerId)[0];
                      return `${currencyFormatted(p2p.amount)} - ${contact.firstName} ${contact.lastName} - ${p2p.memo}`;
                    })()}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>

          <FormControl fullWidth margin="normal">
            <InputLabel>Depositing Account</InputLabel>
            <Select
              value={targetAccountField}
              label="Depositing Account"
              error={targetAccountFieldError}
              helperText={targetAccountFieldErrorMessage}
              onChange={event => { setTargetAccountField(event.target.value); }}>
              {customer.accounts.filter(account => ['checking', 'savings', 'external'].includes(account.type))
                .map(account => {
                  return (
                    <MenuItem
                      key={account.accountId}
                      value={account.accountId}>
                      {account.nickname} {wordFormatted(account.type)} {typeof account.balance == 'number' ? '- Available: ' + currencyFormatted(account.balance) : ""}
                    </MenuItem>);
                })}
            </Select>
          </FormControl>

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
