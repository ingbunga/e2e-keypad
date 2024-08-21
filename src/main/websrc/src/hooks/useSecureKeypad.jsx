"use client";

import { useEffect, useMemo, useState } from 'react';
import axios from "axios";
import { JSEncrypt } from "jsencrypt";

const PUB_KEY = `-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtkLA7dcyLqz4M6BS/XZi
wMee85fjwskmxfZVN/qI854Sa4mlU/5Rse0HcNY0QoF+J3kQF3xWpTKLfw2p5pzt
sALLN6gsO2m4qLIOk3eNR+hVL2Rh4dc8MAhuXfoTGrfMjXouiy05rYgVpqIRRCjz
MVGYnJ7arZ6rMN73nRxd0I9RVbe3LXEuHrBysxjfXae6z+qb+1Rp9MKnwiDuKC/i
2lqqqmV9p/8OuY+qUzsMCtU8URS8kvw/bkg90TEOHzjKWrRIYRcQQkdJ8KuX3/lV
1jBBgIQRfmQVTFUnkV5XBZw9jXYTsz6Bcp4MNWUlwHQIebAM8vMZ6/nH9p4OdETA
5wIDAQAB
-----END PUBLIC KEY-----
`


export default function useSecureKeypad() {
  const [keypad, setKeypad] = useState(null);
  const [userInput, setUserInput] = useState('');
  const KEY_LENGTH = 36; // Depends on UUID

  const getSecureKeypad = () => {
    axios
      .get("http://localhost:8080/pad/get")
      .then(
        res => {
          setKeypad(res.data)
        }
      );
  }

  const getPressCount = () => (userInput.length / KEY_LENGTH)

  const onKeyPressed = (row, col) => {
    if (!keypad.keys[row * 4 + col]) return;
    setUserInput(e => e + keypad.keys[row * 4 + col]);
  }

  useEffect(() => {
    if (getPressCount() >= 6)
      sendUserInput();
  }, [userInput]);

  const sendUserInput = (function () {
    const crypt = new JSEncrypt();
    crypt.setPublicKey(PUB_KEY);

    return () => {
      axios
        .post(
          "http://localhost:8080/pad/submit",
          JSON.stringify({
            padId: keypad.id,
            userInput: crypt.encrypt(userInput),
          }),
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        )
        .then(
          res => {
            alert(res.data.decoded)
            location.reload();
          }
        );
    }
  })();

  return {
    states: {
      keypad,
      userInput
    },
    actions: {
      getSecureKeypad,
      getPressCount,
      onKeyPressed,
      sendUserInput
    }
  }
}
