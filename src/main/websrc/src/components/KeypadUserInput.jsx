import '../style/keypad.css'

export default function KeypadUserInput({ getPressCount }) {
    return (
        <>
            <div className="input-group-style">
                {
                    Array(getPressCount()).fill("🔵").join('') +
                    Array(6 - getPressCount()).fill("⚪️").join('')
                }
            </div>
        </>
    );
}
