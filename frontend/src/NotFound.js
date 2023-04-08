import { useContext, useEffect } from 'react';
import Context from './Context';
import { useNavigate } from 'react-router-dom';
import { Grid, Typography } from '@mui/material';

export default function NotFound() {
  const { api_id } = useContext(Context);
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      navigate('/');
    }, 2000);
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
          Not Found
        </Typography>
      </Grid>

    </Grid>
  );
};
