import { useContext, useEffect } from 'react';
import Context from './Context';
import { useNavigate, useLocation } from 'react-router-dom';
import { Grid, Typography } from '@mui/material';

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
