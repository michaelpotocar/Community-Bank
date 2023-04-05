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
import { currencyFormatted, wordFormatted } from './Utilities';

export default function Transfer() {
  const { api_id } = useContext(Context);
  const { customerId } = useParams();
  const [loading, setLoading] = useState([true]);
  const [customer, setCustomer] = useState('Loading');

  const [transferTypeField, setTransferTypeField] = useState('');
  const [fundingAccountField, setFundingAccountField] = useState('');
  const [targetAccountField, setTargetAccountField] = useState('');
  const [contactField, setContactField] = useState('');
  const [amountField, setNicknameField] = useState('');
  const [amountFieldError, setNicknameFieldError] = useState(false);
  const [amountFieldErrorMessage, setNicknameFieldErrorMessage] = useState('');
  const [memoField, setAcocuntNumberField] = useState('');
  const [memoNumberFieldError, setAcocuntNumberFieldError] = useState(false);
  const [memoNumberFieldErrorMessage, setAccountNumberFieldErrorMessage] = useState('');
  const [routingNumberField, setRoutingNumberField] = useState('');
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

  const changeAmountField = (event) => {
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

  const changeMemoField = (event) => {
    const input = event.target.value;
    setAcocuntNumberField(input);

  };

  const submitter = () => {
    axios.post(`https://${api_id}.execute-api.us-west-2.amazonaws.com/prod/customers/${customerId}/transfer`, {
      type: transferTypeField,
      fundingAccountId: fundingAccountField,
      targetAccountId: targetAccountField,
      targetContactId: contactField,
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
              onChange={event => { setFundingAccountField(event.target.value); }}>
              {customer.accounts.filter(account => ['checking', 'savings', 'external']
                .includes(account.type)).map(account => {
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
              onChange={event => {
                setTargetAccountField(event.target.value);
              }}>
              {(() => {
                if (transferTypeField == 'standard') {
                  return customer.accounts.filter(account => ['checking', 'savings', 'external'].includes(account.type));
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
              value={contactField}
              label="Contact"
              onChange={event => {
                setContactField(event.target.value);
              }}>
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
              onChange={changeAmountField} />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              label="Memo"
              variant="outlined"
              error={memoNumberFieldError}
              helperText={memoNumberFieldErrorMessage}
              value={memoField}
              onChange={changeMemoField} />
          </FormControl>

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
