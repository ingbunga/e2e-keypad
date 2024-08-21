import { useState } from 'react';
import '../style/keypad.css'

function tableify(col, array) {
    let y = [];
    for (let i = 0; i < array.length; i += col) {
        y = [...y, array.slice(i, i + col)];

    }
    return y;
}

export default function SecureKeypad({ keypad, onKeyPressed }) {
    const [keys, _] = useState(_ => tableify(4, keypad.keys) || [[]], [keypad.keys]);

    return (
        <>
            <table className="table-style" 
                style={{width: 200, height: 150, backgroundImage: `url(data:image/png;base64,${keypad.image})`}}>
                <tbody>
                {keys.map((line, i) =>
                    <tr>
                        {line.map((_, j) => <td onClick={e => onKeyPressed(i, j)} />)}
                    </tr>
                )}
                </tbody>
            </table>
        </>
    );
}
