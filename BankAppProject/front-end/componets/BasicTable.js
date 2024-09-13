import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
export default function BasicTable({accounts, handleDelete}) {
  const rows = accounts
  
  const navigate = useNavigate();
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Account Number</TableCell>
            <TableCell align="right">Sort Code</TableCell>
            <TableCell align="right">Account Name</TableCell>
            <TableCell align="right">Opening Balance</TableCell>
            <TableCell align="right">Current Balance</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row["number"]}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row["number"]}
              </TableCell>
              <TableCell align="right">{row["sortCode"]}</TableCell>
              <TableCell align="right">{row["name"]}</TableCell>
              <TableCell align="right">{row["openingBalance"]}</TableCell>
              <TableCell align="right">{row["balance"]}</TableCell>
              <TableCell align="right">
              <TableCell>
                <Button variant="contained" color="primary" onClick={() => navigate('/transactions', {state: row.number})} >Transactions</Button>
                <Button variant="contained" color="secondary" style={{ marginLeft: 10 }} name="delete" value={row.number} onClick={handleDelete}>Close Account</Button>
                <Button variant="contained" color="success" style={{ marginLeft: 10 }} name="moveMoney" value={row.number} onClick={() => navigate('/movemoney')}>Move Money</Button>

              </TableCell>
               </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}