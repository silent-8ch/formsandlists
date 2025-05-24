import UIKit

class SuccessViewController: UIViewController {
    
    private let congratsLabel: UILabel = {
        let label = UILabel()
        label.text = "Good Job!"
        label.font = .systemFont(ofSize: 32, weight: .bold)
        label.textAlignment = .center
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    private var emitter: CAEmitterLayer?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupConfetti()
    }
    
    private func setupUI() {
        view.backgroundColor = .systemBackground
        view.addSubview(congratsLabel)
        
        NSLayoutConstraint.activate([
            congratsLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            congratsLabel.centerYAnchor.constraint(equalTo: view.centerYAnchor)
        ])
    }
    
    private func setupConfetti() {
        let emitter = CAEmitterLayer()
        emitter.emitterPosition = CGPoint(x: view.bounds.width / 2, y: -50)
        emitter.emitterShape = .line
        emitter.emitterSize = CGSize(width: view.bounds.width, height: 1)
        
        let colors: [UIColor] = [.systemRed, .systemBlue, .systemGreen, .systemYellow, .systemPurple]
        let cells: [CAEmitterCell] = colors.map { color in
            let cell = CAEmitterCell()
            cell.birthRate = 4
            cell.lifetime = 10
            cell.lifetimeRange = 0
            cell.velocity = 200
            cell.velocityRange = 50
            cell.emissionLongitude = .pi
            cell.emissionRange = .pi / 4
            cell.spin = 2
            cell.spinRange = 3
            cell.scale = 0.5
            cell.scaleRange = 0.25
            cell.color = color.cgColor
            cell.contents = createConfettiImage()?.cgImage
            return cell
        }
        
        emitter.emitterCells = cells
        view.layer.addSublayer(emitter)
        self.emitter = emitter
    }
    
    private func createConfettiImage() -> UIImage? {
        let size = CGSize(width: 12, height: 12)
        UIGraphicsBeginImageContextWithOptions(size, false, 0)
        defer { UIGraphicsEndImageContext() }
        
        guard let context = UIGraphicsGetCurrentContext() else { return nil }
        context.setFillColor(UIColor.white.cgColor)
        context.fill(CGRect(origin: .zero, size: size))
        
        return UIGraphicsGetImageFromCurrentImageContext()
    }
} 