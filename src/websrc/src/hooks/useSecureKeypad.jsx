"use client";

import {useEffect, useMemo, useState} from 'react';
import axios from "axios";
import {JSEncrypt} from "jsencrypt";

export default function useSecureKeypad() {
  const [keypad, setKeypad] = useState(null);
  const [userInput, setUserInput] = useState('');
  const [userPressCount, _setUserPressCount] = useState(0);

  const getSecureKeypad = () => {
    axios
      .get("http://localhost:8080/pad/get")
      .then(
        res => {
          setKeypad(res.data)
        }
      );
  }

  const onKeyPressed = (row, col) => {
    setUserInput(e => e + keypad.keys[row*4+col]);
    _setUserPressCount(e => e + 1);
  }

  useEffect(() => {
    if (userPressCount >= 6)
      sendUserInput();
  }, [userPressCount]);

  const sendUserInput = () => {
    alert(userInput);
    location.reload();
  }

  return {
    states: {
      keypad,
      userInput,
      userPressCount,
    },
    actions: {
      getSecureKeypad,
      onKeyPressed,
      sendUserInput
    }
  }
}
