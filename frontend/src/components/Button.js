import { Link } from '@mui/material';

function Customers({ children }) {

    return (
        <Link
            variant="contained"
            underline="hover"
            fullWidth={false}>
            {children}
        </Link>
    );
};

export default Customers;

