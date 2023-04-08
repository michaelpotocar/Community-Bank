import { useState, useMemo, useContext, useEffect } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Button, Grid, Typography, FormControl, InputLabel, Select, MenuItem, TextField } from '@mui/material';
import { currencyFormatted, wordFormatted } from './Utilities';

export default function Transfer() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();

  const [loading, setLoading] = useState([true]);
  const [customer, setCustomer] = useState({});

  const [transferTypeField, setTransferTypeField] = useState('');

  const [fundingAccountField, setFundingAccountField] = useState('');
  const [fundingAccountFieldError, setFundingAccountFieldError] = useState(false);
  const [fundingAccountFieldErrorMessage, setFundingAccountFieldErrorMessage] = useState('');

  const [targetAccountField, setTargetAccountField] = useState('');
  const [targetAccountFieldError, setTargetAccountFieldError] = useState(false);
  const [targetAccountFieldErrorMessage, setTargetAccountFieldErrorMessage] = useState('');

  const [targetContactField, setTargetContactField] = useState('');
  const [targetContactFieldError, setTargetContactFieldError] = useState(false);
  const [targetContactFieldErrorMessage, setTargetContactFieldErrorMessage] = useState('');

  const [amountField, setAmountField] = useState('');
  const [amountFieldError, setAmountFieldError] = useState(false);
  const [amountFieldErrorMessage, setAmountFieldErrorMessage] = useState('');

  const [memoField, setMemoField] = useState('');

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

  function validateAmountField() {
    const regex = /^\d+\.?\d{0,2}$/;
    const regexPass = regex.test(amountField.trim());
    if (!regexPass) {
      setAmountFieldError(true);
      setAmountFieldErrorMessage("Amount must be in form of XXX.xx");
      return true;
    }

    setAmountFieldError(false);
    setAmountFieldErrorMessage('');
    return false;
  };

  function validateFundingAccountField() {
    const validAccountIds = customer.accounts?.filter(account => ['checking', 'savings', 'external'].includes(account.type))
      .map(account => account.accountId);

    if (!validAccountIds?.includes(fundingAccountField)) {
      setFundingAccountFieldError(true);
      setFundingAccountFieldErrorMessage("Must Select a valid Account");
      return true;
    }

    setFundingAccountFieldError(false);
    setFundingAccountFieldErrorMessage('');
    return false;
  }

  function validateTargetAccountField() {
    let validAccountIds;
    if (transferTypeField == 'standard') {
      validAccountIds = customer.accounts?.filter(account => ['checking', 'savings', 'external'].includes(account.type))
        .map(account => account.accountId);

    } else if (transferTypeField == 'credit') {
      validAccountIds = customer.accounts?.filter(account => ['credit'].includes(account.type))
        .map(account => account.accountId);
    }

    if (!validAccountIds?.includes(targetAccountField)) {
      setTargetAccountFieldError(true);
      setTargetAccountFieldErrorMessage("Must Select a valid Account");
      return true;
    }

    setTargetAccountFieldError(false);
    setTargetAccountFieldErrorMessage('');
    return false;
  }

  function validateTargetContactField() {
    let validContactIds = customer.contacts?.map(contact => contact.id);

    if (!validContactIds?.includes(targetContactField)) {
      setTargetContactFieldError(true);
      setTargetContactFieldErrorMessage("Must Select a valid Contact");
      return true;
    }

    setTargetContactFieldError(false);
    setTargetContactFieldErrorMessage('');
    return false;
  }

  function validateSubmitButton() {
    let invalidAmountField = validateAmountField();
    let invalidFundingAccountField = validateFundingAccountField();
    if (transferTypeField == 'standard' || transferTypeField == 'credit') {
      let invalidTargetAccountField = validateTargetAccountField();
      setSubmitDisabled(invalidAmountField || invalidFundingAccountField || invalidTargetAccountField);
    } else if (transferTypeField == 'p2p') {
      let invalidTargetContactField = validateTargetContactField();
      setSubmitDisabled(invalidAmountField || invalidFundingAccountField || invalidTargetContactField);
    } else {
      setSubmitDisabled(true);
    }
  }

  useEffect(() => {
    validateSubmitButton();
  });


  const submitter = () => {
    axios.post(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/transfer`, {
      type: transferTypeField,
      fundingAccountId: fundingAccountField,
      targetAccountId: targetAccountField,
      targetContactId: targetContactField,
      amount: amountField,
      memo: memoField
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
          Transfer Funds
        </Typography>
      </Grid>
      <Grid item xs={4} />

      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={8}>
        <Grid container
          justifyContent="center">

          <FormControl fullWidth margin="normal">
            <InputLabel>Transfer Type</InputLabel>
            <Select
              value={transferTypeField}
              label="Transfer Type"
              onChange={event => {
                setTransferTypeField(event.target.value);
              }}>
              <MenuItem value={"standard"}>Standard</MenuItem>
              <MenuItem value={"credit"}>Pay Credit Card</MenuItem>
              <MenuItem value={"p2p"}>Peer to Peer</MenuItem>
            </Select>
          </FormControl>

          <FormControl fullWidth margin="normal">
            <InputLabel>Funding Account</InputLabel>
            <Select
              value={fundingAccountField}
              label="Funding Account"
              error={fundingAccountFieldError}
              helperText={fundingAccountFieldErrorMessage}
              onChange={event => { setFundingAccountField(event.target.value); }}>
              {customer.accounts.filter(account => ['checking', 'savings', 'external'].includes(account.type) && account.accountId !== targetAccountField).map(account => {
                return (
                  <MenuItem
                    key={account.accountId}
                    value={account.accountId}>
                    {account.nickname} {wordFormatted(account.type)} {typeof account.balance == 'number' ? '- Available: ' + currencyFormatted(account.balance) : ""}
                  </MenuItem>);
              })}
            </Select>
          </FormControl>

          {(transferTypeField == 'standard' || transferTypeField == 'credit') && <FormControl fullWidth margin="normal">
            <InputLabel>Target Account</InputLabel>
            <Select
              value={targetAccountField}
              label="Target Account"
              error={targetAccountFieldError}
              helperText={targetAccountFieldErrorMessage}
              onChange={event => { setTargetAccountField(event.target.value); }}>
              {(() => {
                if (transferTypeField == 'standard') {
                  return customer.accounts.filter(account => ['checking', 'savings', 'external'].includes(account.type) && account.accountId !== fundingAccountField);
                } else if (transferTypeField == 'credit') {
                  return customer.accounts.filter(account => ['credit'].includes(account.type));
                } else {
                  return [];
                }
              })().map(account => {
                return (
                  <MenuItem
                    key={account.accountId}
                    value={account.accountId}>
                    {account.nickname} {wordFormatted(account.type)} {typeof account.balance == 'number' ? `- ${account.type == 'credit' ? 'Balance' : 'Available'}: ` + currencyFormatted(account.balance) : ""}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>}

          {transferTypeField == 'p2p' && <FormControl fullWidth margin="normal">
            <InputLabel>Contact</InputLabel>
            <Select
              value={targetContactField}
              label="Contact"
              error={targetContactFieldError}
              helperText={targetContactFieldErrorMessage}
              onChange={event => { setTargetContactField(event.target.value); }}>
              {customer.contacts.filter(() => true).map(contact => {
                return (
                  <MenuItem
                    key={contact.id}
                    value={contact.id}>
                    {contact.firstName} {contact.lastName}
                  </MenuItem>
                );
              })}

            </Select>
          </FormControl>}

          <FormControl fullWidth margin="normal">
            <TextField
              label="Amount"
              variant="outlined"
              error={amountFieldError}
              helperText={amountFieldErrorMessage}
              value={amountField}
              onChange={(event) => { setAmountField(event.target.value); }} />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              label="Memo"
              variant="outlined"
              value={memoField}
              onChange={(event) => { setMemoField(event.target.value); }} />
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
