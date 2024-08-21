import '../style/keypad.css'

export default function KeypadUserInput({ userPressCount }) {
    return (
        <>
            <div className="input-group-style">
                {
                    Array(userPressCount).fill("🔵").join('') +
                    Array(6 - userPressCount).fill("⚪️").join('')
                }
            </div>
        </>
    );
}
