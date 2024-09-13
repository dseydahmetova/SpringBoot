





import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useNavigate, useLocation } from 'react-router-dom';
import { useMemo, useState } from 'react';


export default function Transactions() {
  const location = useLocation();
  const id = location.state
  const [transactions, setTransactions] = useState([]);
  const navigate = useNavigate();
  console.log(id)
  useMemo(() => {
    fetch(`http://localhost:8080/account/${id}`)
        .then(res => res.json())
        .then(data => setTransactions(data["transactions"]))
        .catch(err => console.log(err));}, 
    [])

  return (
    <TableContainer component={Paper} sx={{ marginTop: '4rem'}}>
      <Table sx={{ minWidth: 650, }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Date</TableCell>
            <TableCell align="right">Type</TableCell>
            <TableCell align="right">Amount</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {transactions && transactions.map((transaction) => (
            <TableRow
              key={transaction["time"]}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {transaction["time"]}
              </TableCell>
              <TableCell component="th" scope="row">
                {transaction["type"]}
              </TableCell>
              <TableCell align="right">{transaction["amount"]}</TableCell>
        
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}