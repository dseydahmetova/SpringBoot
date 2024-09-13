import { Outlet, Link } from "react-router-dom";
import Grid from "@mui/material/Grid2";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

const linkStyle = { color: 'inherit', textDecoration: 'none' };


export default function Layout() {
    return (
    <Grid  container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center">
        <Grid size={12}>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar position="static">
                    <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        <Link to="/" style={linkStyle}>NovaShield Bank</Link>
                    </Typography>
                    <Button color="inherit"><Link to="/login" style={linkStyle}>Login</Link></Button>
                    </Toolbar>
                </AppBar>
                </Box>
        </Grid>
        <Grid  container
            spacing={0}
            direction="column"
            alignItems="center"
            justifyContent="center"
            sx={{ marginX: 'auto' }}>
            <Outlet />
        </Grid>
    </Grid>
    );
};