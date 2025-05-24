import UIKit
import GoogleMobileAds

class ListItem {
    let id: Int
    let text: String
    
    init(id: Int) {
        self.id = id
        self.text = "List Item \(Int.random(in: 100...999))"
    }
}

class ListViewController: UIViewController {
    private var items: [ListItem] = []
    private let maxItems = 3
    private var isSuccess = false
    private var bannerView: GADBannerView!
    
    private let tableView: UITableView = {
        let table = UITableView()
        table.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
        table.translatesAutoresizingMaskIntoConstraints = false
        return table
    }()
    
    private let emptyStateLabel: UILabel = {
        let label = UILabel()
        label.text = "Tap + to add items"
        label.textAlignment = .center
        label.textColor = .secondaryLabel
        label.font = .systemFont(ofSize: 17)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        setupBannerAd()
    }
    
    private func setupUI() {
        view.backgroundColor = .systemBackground
        title = "Add three items"
        
        // Add button in navigation bar
        navigationItem.rightBarButtonItem = UIBarButtonItem(
            barButtonSystemItem: .add,
            target: self,
            action: #selector(addButtonTapped)
        )
        
        // Add table view
        view.addSubview(tableView)
        tableView.delegate = self
        tableView.dataSource = self
        
        // Add empty state label
        view.addSubview(emptyStateLabel)
        
        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            tableView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            emptyStateLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            emptyStateLabel.centerYAnchor.constraint(equalTo: view.centerYAnchor)
        ])
    }
    
    private func setupBannerAd() {
        // Create and setup banner view
        bannerView = GADBannerView(adSize: GADAdSizeBanner)
        bannerView.adUnitID = AdConfig.bannerID
        bannerView.rootViewController = self
        bannerView.delegate = self
        bannerView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(bannerView)
        
        // Update constraints
        NSLayoutConstraint.activate([
            bannerView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            bannerView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            bannerView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            
            // Update table view bottom constraint
            tableView.bottomAnchor.constraint(equalTo: bannerView.topAnchor)
        ])
        
        // Load banner ad
        bannerView.load(GADRequest())
    }
    
    @objc private func addButtonTapped() {
        guard items.count < maxItems else { return }
        
        items.append(ListItem(id: items.count))
        tableView.reloadData()
        
        // Update empty state visibility
        emptyStateLabel.isHidden = !items.isEmpty
        
        // Check if we've reached the target
        if items.count == maxItems {
            showSuccess()
        }
        
        // Update add button state
        navigationItem.rightBarButtonItem?.isEnabled = items.count < maxItems
    }
    
    private func showSuccess() {
        isSuccess = true
        title = "Good Job!"
        
        // Reload to update cell appearances
        tableView.reloadData()
        
        // Add confetti
        let successVC = SuccessViewController()
        successVC.modalPresentationStyle = .overCurrentContext
        successVC.modalTransitionStyle = .crossDissolve
        successVC.view.backgroundColor = .clear
        present(successVC, animated: true)
    }
}

extension ListViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        emptyStateLabel.isHidden = !items.isEmpty
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let item = items[indexPath.row]
        
        var config = cell.defaultContentConfiguration()
        config.text = item.text
        cell.contentConfiguration = config
        
        if isSuccess {
            cell.backgroundColor = .systemBlue.withAlphaComponent(0.1)
            config.textProperties.color = .systemBlue
            cell.contentConfiguration = config
        } else {
            cell.backgroundColor = .secondarySystemBackground
            config.textProperties.color = .label
            cell.contentConfiguration = config
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 64 // Match Android's height
    }
}

// MARK: - GADBannerViewDelegate
extension ListViewController: GADBannerViewDelegate {
    func bannerViewDidReceiveAd(_ bannerView: GADBannerView) {
        print("Banner ad loaded successfully")
    }
    
    func bannerView(_ bannerView: GADBannerView, didFailToReceiveAdWithError error: Error) {
        print("Banner ad failed to load with error: \(error.localizedDescription)")
    }
} 
