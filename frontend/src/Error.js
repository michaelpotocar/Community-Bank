import { useState, useMemo, useContext, useEffect } from 'react';
import axios from 'axios';
import Context from './Context';
import { useParams, Link, useNavigate, useLocation } from 'react-router-dom';
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

export default function Error() {
  const { api_id } = useContext(Context);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    setTimeout(() => {
      navigate('/');
    }, 6000);
  }, []);

  return (
    <Grid container
      spacing={1}
      justifyContent="center">

      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />
      <Grid item xs={12} />

      <Grid item xs={12}>
        <Typography align='center' variant="h3">
          {location.state}
        </Typography>
      </Grid>

    </Grid>
  );
};
